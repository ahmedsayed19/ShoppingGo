from django.contrib import admin
from server.models import *
# from server.models import Product

# # Register your models here.
class ProductAdmin(admin.ModelAdmin):
    pass

admin.site.register(Product, ProductAdmin)