import TabsView from '@/layouts/tabs/TabsView'
import BlankView from '@/layouts/BlankView'
// import PageView from '@/layouts/PageView'

// 路由配置
const options = {
  routes: [
    {
      path: '/login',
      name: '登录页',
      component: () => import('@/pages/login')
    },
    {
      path: 'register',
      name: '注册页',
      component: () => import('@/pages/register/Register')
    },
    {
      path: '*',
      name: '404',
      component: () => import('@/pages/exception/404'),
    },
    {
      path: '/403',
      name: '403',
      component: () => import('@/pages/exception/403'),
    },
    {
      path: '/',
      name: '首页',
      component: TabsView,
      redirect: '/login',
      children: [
        {
          path: 'dashboard',
          name: 'Dashboard',
          meta: {
            icon: 'dashboard'
          },
          // component: BlankView,
          component: () => import('@/pages/dashboard/analysis'),
        },
        {
          path: 'form',
          name: '漏洞上报',
          meta: {
            icon: 'form',
            page: {
              cacheAble: false
            }
          },
          // component: PageView,
          component: () => import('@/pages/form/basic'),
        },
        {
          path: 'list',
          name: '漏洞列表',
          meta: {
            icon: 'table'
          },
          // component: PageView,
          component: () => import('@/pages/list/QueryList'),
        },
        {
          path: 'details',
          name: '漏洞详情',
          meta: {
            icon: 'profile',
            invisible: true
          },
          component: BlankView,
          children: [
            {
              path: 'basic',
              name: '基础详情页',
              component: () => import('@/pages/detail/BasicDetail')
            },
            {
              path: 'edit',
              name: '编辑页',
              component: () => import('@/pages/detail/Edit')
            }
          ]
        },
        {
          path: 'intelligence',
          name: '威胁情报',
          meta: {
            icon: 'profile'
          },
        },
        {
          path: 'kgraph',
          name: '漏洞图谱',
          meta: {
            icon: 'profile'
          },
          component: () => import('@/pages/kg/Graph')
        },
        {
          path: 'about',
          name: '关于我们',
          meta: {
            icon: 'profile'
          }
        }
      ]
    },
    {
      path: '/account',
      name: '个人页',
      component: TabsView,
      meta: {
      },
      children: [
        {
          path: '/account/center',
          name: '个人中心',
          component: () => import('@/pages/account/center/index')
        },
        {
          path: '/settings',
          name: '个人设置',
          // component: () => import('@pages/account/settings')
        }
      ]
    },
  ]
}

export default options
