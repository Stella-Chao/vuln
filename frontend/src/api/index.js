import request from '../utils/request';

export const fetchData = query => {
    return request({
        url: '/api/data',
        method: 'get',
        params: query
    });
};

export const getAllData = () => {
    return request({
        url: '/api/data',
        method: 'get'
    });
};

export const getNewData = () => {
    return request({
        url: '/',
        method: 'get'
    })
}

export const getList = (page) => {
    return request({
        url: '/api/data',
        method: 'get',
        params: page
    })
}

export const getDetail = params => {
    return request({
        url: '/api/data',
        method: 'get',
        params: params
    })
}

export const getLeakByCVE = cve => {
    return request({
        url: '/api/leak/'+cve,
        method: 'get',
    })
}
