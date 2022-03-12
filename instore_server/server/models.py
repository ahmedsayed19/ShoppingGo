from django.db import models

# Create your models here.

class Product(models.Model):
    id = models.IntegerField(primary_key=True)
    name = models.CharField(max_length=200)
    img = models.ImageField()
    price = models.IntegerField()
    weight = models.IntegerField()
    # catigory = models.CharField(max_length=5, choices=CATIGORY_CHOICES)
