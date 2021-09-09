
# Create your views here.
import json
from .models import Leak
from rest_framework.generics import ListCreateAPIView
from .pagination import LeakPageNumberPagination
from .serializer import LeakSerializer
from django.http import JsonResponse
from django.core.serializers import serialize

class LeakViewSet(ListCreateAPIView):
    queryset = Leak.objects.all()[:100]
    serializer_class = LeakSerializer
    pagination_class = LeakPageNumberPagination

    # def getDetail(self):
    #     name = self.request.GET.get('name')
    #     if name != None:
    #         return Leak.objects.filter(name=name)

class LeakViewSet2(ListCreateAPIView):
    print('LeakViewSet2...')
    serializer_class = LeakSerializer
    def get(self, request, cve, *args, **kwargs):
        print('get请求...')
        queryset = Leak.objects.filter(name = cve)
        json_data = serialize('json', queryset)
        json_data = json.loads(json_data)
        if request.method == 'GET':
            return JsonResponse(json_data, safe=False)

