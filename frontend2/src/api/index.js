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

export const getVulnDetail = params => {
    return request({
        url: '${base}/vuln',
        method: 'get',
        params: params
    })
}
