from rest_framework import generics
from server.models import *
from server.serializer import *



class ProductList(generics.ListAPIView):
    queryset = Product.objects.all()
    serializer_class = ProductSerializer


class ProductDetails(generics.RetrieveAPIView):
    queryset = Product.objects.all()
    serializer_class = ProductSerializer



# from django.conf import settings
# from rest_framework import status
# from rest_framework.response import Response
# from rest_framework.views import APIView
# from django.http import HttpResponse
# import requests
# from rest_framework.decorators import api_view



# class ImageHalabesa(APIView):
#     def post(self, request):
#         # data = request.data
#         serialized = ProductSerializer(data=request.data)
#         if serialized.is_valid():
#             return Response(serialized.data)


# # Auth Token
# token = requests.post('https://accept.paymob.com/api/auth/tokens',
#                       json={"api_key": settings.PAYMENT_API_KEY}).json()

# # act as a nosql database
# database = []

# class RegesterAnOrder(APIView):
#     def post(self, request):
# # Auth Token
# token = requests.post('https://accept.paymob.com/api/auth/tokens',
#                       json={"api_key": settings.PAYMENT_API_KEY}).json()

# # act as a nosql database
# database = []
#         data = request.data

#         # Order Registration API
#         orderRegistrationPayload = {
#             "auth_token":  token['token'],
#             "delivery_needed": "false",
#             "amount_cents": data['total'],
#             "currency": settings.CURRENCY,
#             # "items": [
#             #     {
#             #         "name": item['barcode'],
#             #         "amount_cents": item['price'],
#             #         "description": item['name'],
#             #         "quantity": item['quantity']
#             #     }
#             #     for item in data['items']
#             # ],
#         }
#         # try:
#         orderRegistration = requests.post(
#             'https://accept.paymob.com/api/ecommerce/orders', json=orderRegistrationPayload).json()

#         if 'token' not in orderRegistration.keys():
#             return Response(orderRegistration)


#         # Invioce Link
#         invoicePayload = {
#             "auth_token": token['token'],
#             "api_source": "INVOICE",
#             "amount_cents":  data['total'],
#             "currency": settings.CURRENCY,
#             "items": [
#                 {
#                     "name": item['barcode'],
#                     "amount_cents": item['price'],
#                     "description": item['name'],
#                     "quantity": item['quantity']
#                 }
#                 for item in data['items']
#             ],
#             "shipping_data": {
#                 "first_name": "SmartCart",
#                 "last_name": 'NA',
#                 "phone_number": 'NA',
#                 "email":'NA'
#             },
            # "integrations": []
#             # "merchant_order_id": data["order"]["merchant_order_id"],
#             "delivery_needed": "false"

#         }
#         invoice = requests.post('https://accept.paymobsolutions.com/api/ecommerce/orders', json=invoicePayload).json()

#         # print(invoicePayload)
#         return Response(invoice)
#         # Payment Key Request
        
# def mobileWallet(paymentKey):
#     # Mobile Wallet Pay Request
#     payReqPayload = {
#         "source": {
#             "identifier": "01010101010",
#             "subtype": "WALLET"
#         },
#         "payment_token": paymentKey['token']
#     }
#     result = requests.post(
#         'https://accept.paymob.com/api/acceptance/payments/pay', json=payReqPayload)
#     print(result.json())
#     return Response(result.json())
#     # return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
# # https://accept.paymobsolutions.com/api/acceptance/post_pay
# # https://accept.paymobsolutions.com/api/acceptance/post_pay


# class RedirectedUrl(APIView):

#     def post(self, request):
#         data = request.data['obj']
#         shipping_data = request.data['obj']['order']['shipping_data']
#         invoicePayload = {
#             "auth_token": token['token'],
#             "api_source": "INVOICE",
#             "amount_cents": data['order']["amount_cents"],
#             "currency": data["currency"],
#             "shipping_data": {
#                 "first_name": shipping_data["first_name"],
#                 "last_name": shipping_data["last_name"],
#                 "phone_number": shipping_data["phone_number"],
#                 "email": shipping_data["email"]
#             },
#             "integrations": [data['integration_id'], 1835438],
#             # "merchant_order_id": data["order"]["merchant_order_id"],



#             "items": data["order"]["items"],

#             "delivery_needed": "false"

#         }
#         # invoice = requests.post('https://accept.paymobsolutions.com/api/ecommerce/orders', json=invoicePayload).json()

#         # print(invoice)
#         # print("URL ",invoice['url'])
#         # database.append(data)
#         return Response()


# @api_view(('GET',))
# def index(request):
#     payload = {
#         "items": request.GET.get('items',''),
#         "success" :request.GET.get('success',False), 
#         "total": request.GET.get('amount_cents',0)
#     }


#     return Response(payload)



# database.append({
#             'token': 'M6hMv',
#             "amount_cents": data['total'],
#             "items": [
#                 {
#                     "name": item['barcode'],
#                     "amount_cents": item['price'],
#                     "description": item['name'],
#                     "quantity": item['quantity']
#                 }
#                 for item in data['items']
#             ],
#         })