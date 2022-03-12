from django.urls import path

from . import views

urlpatterns = [
    path('', views.ProductList.as_view(),  name='index'),
    path('product/<pk>', views.ProductDetails.as_view(),  name='product_detail'),
    # path('pay/', views.RegesterAnOrder.as_view()),
    # path('feedback/', views.RedirectedUrl.as_view()),
    # path('index/', views.index)
]
