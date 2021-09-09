
from django.urls import path
from .views import LeakViewSet,LeakViewSet2

urlpatterns = [
    path('data/', LeakViewSet.as_view()),
    path('leak/<str:cve>', LeakViewSet2.as_view())
]