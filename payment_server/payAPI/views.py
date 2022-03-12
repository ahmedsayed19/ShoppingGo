
from rest_framework.response import Response
from rest_framework.views import APIView
from django.conf import settings
from rest_framework import status
import requests
from rest_framework.decorators import api_view
from rest_framework.renderers import TemplateHTMLRenderer
from .models import *


# Auth Token
token = requests.post('https://accept.paymob.com/api/auth/tokens',
                      json={"api_key": settings.PAYMENT_API_KEY}).json()


# Create your views here.
class RegesterAnOrder(APIView):
    def post(self, request):
        ''' '''
        data = request.data
        items = [
                {
                    "name": item['barcode'],
                    "amount_cents": item['price'],
                    "description": item['name'],
                    "quantity": item['quantity']
                }
                for item in data['items']
            ]
        # Order Registration API
        orderRegistrationPayload = {
            "auth_token":  token['token'],
            "delivery_needed": "false",
            "amount_cents": data['total'],
            "currency": settings.CURRENCY,
            "items": items
        }
        # try:
        orderRegistration = requests.post(
            'https://accept.paymob.com/api/ecommerce/orders', json=orderRegistrationPayload).json()

        if 'token' not in orderRegistration.keys():
            return Response(orderRegistration, status=status.HTTP_400_BAD_REQUEST)


        # Invioce Link
        invoicePayload = {
            "auth_token": token['token'],
            "api_source": "INVOICE",
            "amount_cents":  data['total'],
            "currency": settings.CURRENCY,
            "items": items,
            "shipping_data": {
                "first_name": "SmartCart",
                "last_name": 'NA',
                "phone_number": 'NA',
                "email":'NA'
            },
            "integrations": [settings.WALLET_ID, settings.CARD_ID],
            # "merchant_order_id": data["order"]["merchant_order_id"],
            "delivery_needed": "false"

        }
        invoice = requests.post('https://accept.paymobsolutions.com/api/ecommerce/orders', json=invoicePayload).json()
        i = Invoice.objects.create(
            id = invoice['id'],
            token = invoice['token'],
            items = invoice['items'],
            total_cents = invoice['amount_cents'],
            created_at = invoice['created_at']
        )
        i.save()
        # print(invoicePayload)
        
        return Response(invoice)
        # Payment Key Request{"status": "OK Bro"}



class RedirectedUrl(APIView):
    renderer_classes = [TemplateHTMLRenderer]
    template_name = 'feedback.html'

    def get(self, request):
        invoice = Invoice.objects.get(id=request.GET.get('order','')) or None
        print(invoice)
        payload = {
            "success" :request.GET.get('success',False), 
            "total_cents": invoice.total_cents,
            "items": invoice.items,
            "created_at": invoice.created_at
        }
        
        return Response(payload)


@api_view(('GET',))
def index(request):
    payload = {
        "success" :request.GET.get('success',False), 
        "total": request.GET.get('amount_cents',0)
    }
    return Response(payload)