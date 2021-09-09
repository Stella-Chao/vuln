from rest_framework import serializers
from .models import Leak

class LeakSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Leak
        fields = '__all__'