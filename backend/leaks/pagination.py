from rest_framework.pagination import PageNumberPagination

class LeakPageNumberPagination(PageNumberPagination):
    page_query_param = "page"
    page_size_query_param = "page_size"
    page_size = 20
    max_page_size = 100