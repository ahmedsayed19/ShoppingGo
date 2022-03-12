from djongo import models

# Create your models here.
class Product(models.Model):
    # _id = models.ObjectIdField()
    name = models.CharField(max_length=200)
    description = models.CharField(max_length=200)
    quantity = models.IntegerField()
    amount_cents = models.IntegerField()
    class Meta:
        abstract=True
    
class Invoice(models.Model):
    # _id = models.ObjectIdField()
    id = models.IntegerField(primary_key=True)
    token = models.CharField(max_length=50)
    items = models.ArrayField(model_container=Product)
    total_cents = models.IntegerField()
    created_at = models.DateTimeField()