from django.urls import path

from . import views

urlpatterns = [
    path('pay/', views.RegesterAnOrder.as_view()),
    path('index/', views.RedirectedUrl.as_view()),
    ]