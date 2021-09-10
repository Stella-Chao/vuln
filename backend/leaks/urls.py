
from django.urls import path
from .views import LeakViewSet,LeakViewSet2,DashBoard

urlpatterns = [
    path('data/', LeakViewSet.as_view()),
    path('leak/<str:cve>', LeakViewSet2.as_view()),
    path('dashboard/', DashBoard.as_view())
]