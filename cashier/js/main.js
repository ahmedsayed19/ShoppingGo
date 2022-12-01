async function putData(url = '', data = {}) {
    // Default options are marked with *
    const response = await fetch(url, {
        method: 'PUT', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        body: JSON.stringify(data) // body data type must match "Content-Type" header
    });
    // return response.json(); // parses JSON response into native JavaScript objects
}

const BASE_URL = 'http://localhost:8000/'
// const BASE_URL = 'http://192.168.43.191:8000/'

function customer_widget(id, Total, Cart) {
    const container = document.querySelector('.carts')
    let frag = new DocumentFragment();
    let customer = document.createElement('div')
    let link = document.createElement('a')
    let total_price = document.createElement('div')
    let total = document.createElement('span')
    let price = document.createElement('span')
    let cart = document.createElement('span')
    let name = document.createElement('span')
    let cart_name = document.createElement('div')
    let button = document.createElement('button')
    let items_list = document.createElement('div')

    customer.classList.add('row', 'customer')

    link.classList.add('link', 'align-baseline')
    link.setAttribute("data-bs-toggle", 'collapse')
    link.setAttribute("href", `#invoice-${id}`)
    link.setAttribute("role", "button")
    link.setAttribute("aria-expanded", "false")
    link.setAttribute("aria-controls", "collapseExample")
    link.addEventListener("click", invoice_details)

    total_price.classList.add('total-price', 'col-2')
    total.classList.add('total')
    price.classList.add('price')
    price.innerText = Total
    total.innerText = "Total"
    cart_name.classList.add('cart-name', 'col-2')
    cart.classList.add('cart')
    name.classList.add('name')
    name.innerText = Cart
    cart.innerText = "Cart"

    button.classList.add('align-baseline', 'btn', 'btn-success', 'col-2', 'justify-content-end')
    button.innerText = "Paid"
    button.setAttribute("type", 'button')
    button.setAttribute("value", id)
    button.setAttribute("onclick", "pay(this)")

    items_list.classList.add('collapse', 'customer')
    items_list.setAttribute("id", `invoice-${id}`)

    total_price.appendChild(total)
    total_price.appendChild(price)
    cart_name.appendChild(cart)
    cart_name.appendChild(name)
    link.appendChild(total_price)
    link.appendChild(cart_name)

    customer.appendChild(link)
    customer.appendChild(button)
    frag.append(customer)
    container.appendChild(customer)
    container.appendChild(items_list)
    return frag

}



function invoice_widget(id, items) {
    const container = document.querySelector(`#invoice-${id}`)
    let frag = new DocumentFragment();
    let table = document.createElement('table')
    let thead = document.createElement('thead')
    let tr = document.createElement('tr')
    let th_no = document.createElement('th')
    let th_product = document.createElement('th')
    let th_quantity = document.createElement('th')
    let th_price = document.createElement('th')
    let tbody = document.createElement('tbody')

    table.classList.add('table', 'table-striped')

    th_no.setAttribute('scope', 'col')
    th_product.setAttribute('scope', 'col')
    th_quantity.setAttribute('scope', 'col')
    th_price.setAttribute('scope', 'col')
    th_no.innerText = '#'
    th_product.innerText = 'Product'
    th_quantity.innerText = 'Quantity'
    th_price.innerText = 'Price'

    tr.appendChild(th_no)
    tr.appendChild(th_product)
    tr.appendChild(th_quantity)
    tr.appendChild(th_price)

    thead.appendChild(tr)

    for (let i = 0; i < items.length; i++) {
        let body_tr = document.createElement('tr')
        let body_th_no = document.createElement('th')
        let body_td_product = document.createElement('td')
        let body_td_quantity = document.createElement('td')
        let body_td_price = document.createElement('td')

        body_th_no.setAttribute('scope', 'col')
        body_th_no.innerText = i + 1
        body_td_product.innerText = items[i].name
        body_td_quantity.innerText = items[i].quantity
        body_td_price.innerText = items[i].price

        body_tr.appendChild(body_th_no)
        body_tr.appendChild(body_td_product)
        body_tr.appendChild(body_td_quantity)
        body_tr.appendChild(body_td_price)

        tbody.appendChild(body_tr)
    }

    table.appendChild(thead)
    table.appendChild(tbody)

    frag.appendChild(table)
    container.appendChild(frag)

    return frag

}

function invoice_details(e) {
    let id = parseInt(e.path[2].href.split('-')[1])

    let container = document.querySelector(`#invoice-${id}`)

    if (container.classList.contains("loaded")) {
        return
    }
    container.classList.add("loaded")

    fetch(`${BASE_URL}cashier/invoices/${id}`)
        .then(response => response.json())
        .then(data => {
            console.log("Server Call for details of incoice", id)
            invoice_widget(id, data.items)
        });

}


function pay(e) {
    console.log(e.value)
    putData(`${BASE_URL}payment/payment_feedback/${e.value}`, data =
        { "success": true })
        .then(data => {
            console.log(e.parentElement)
            e.parentElement.remove()
            let invoice_details = document.querySelector(`#invoice-${e.value}`)
            invoice_details.remove()

        })
}


var eventSource = new EventSource(`${BASE_URL}cashier/dynamic_invoices/`);

eventSource.onopen = function () {
    console.log("We can liten to the server");
};
eventSource.onmessage = function (e) {
    body = JSON.parse(e.data)
    console.log("run", body)
    customer_widget(body.id, body.total / 100, body.cart)

};
eventSource.onerror = function (e) {
    console.log('Error', e);
    // eventSource.close()
}


fetch(`${BASE_URL}cashier/invoices/`)
    .then(response => response.json())
    .then(data => {
        data.forEach(element => {
            customer_widget(element.id, element.total / 100, element.cart)
            console.log(data)
        });
    });
