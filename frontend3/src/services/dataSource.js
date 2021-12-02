import {TEST,GOODS, GOODS_COLUMNS,VULN} from './api'
import {METHOD, request} from '@/utils/request'

export async function goodsList(params) {
  return request(GOODS, METHOD.GET, params)
}

export async function goodsColumns() {
  return request(GOODS_COLUMNS, METHOD.GET)
}

export async function vulnList() {
  return request(VULN, METHOD.GET)
}

export async function vulnTest() {
  return request(TEST, METHOD.GET)
}

export default {goodsList, goodsColumns, vulnList, vulnTest}