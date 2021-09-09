
import leaks.urls
from django.contrib import admin
from django.urls import path, include
from leaks.views import LeakViewSet
from rest_framework_swagger.views import get_swagger_view

schema_view = get_swagger_view(title='API文档')
# router = routers.DefaultRouter()
# router.register('leak', LeakViewSet)

urlpatterns = [
    path(r'docs/', schema_view),
    path('admin/', admin.site.urls),
    path('api/', include(leaks.urls))
]
