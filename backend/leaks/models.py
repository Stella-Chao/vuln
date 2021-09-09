from django.db import models

# Create your models here.
class Leak(models.Model):
    name = models.CharField(max_length=50)
    url = models.CharField(max_length=50)
    description = models.CharField(max_length=500)
    cvss2_nvd_base_score = models.CharField(max_length=50)
    cvss2_nvd_vector = models.CharField(max_length=50)
    cvss3_nvd_base_score = models.CharField(max_length=50)
    cvss3_nvd_vector = models.CharField(max_length=50)
    refer = models.CharField(max_length=50)
    cwe_id = models.CharField(max_length=50)
    cwe_name = models.CharField(max_length=50)
    cpe = models.CharField(max_length=50)
    class Meta:
        db_table = 'nvd'