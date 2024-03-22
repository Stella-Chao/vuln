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
          path: 'sublist',
          name: '提交列表',
          meta: {
            icon: 'form',
            page: {
              cacheAble: false
            }
          },
          // component: PageView,
          component: () => import('@/pages/list/SubmitList'),
        },
        {
          path: 'list',
          name: 'IoT漏洞列表',
          meta: {
            icon: 'table'
          },
          // component: PageView,
          component: () => import('@/pages/list/QueryList'),
        },
        {
          path: 'cve',
          name: 'CVE',
          meta: {
            icon: 'table'
          },
          // component: PageView,
          component: () => import('@/pages/list/QueryList2'),
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
              path: 'basic2',
              name: '基础详情页2',
              component: () => import('@/pages/detail/BasicDetail2')
            },
            {
              path: 'subdetail',
              name: '提交详情页',
              component: () => import('@/pages/detail/SubmitDetail')
            },
            {
              path: 'edit',
              name: '编辑页',
              component: () => import('@/pages/detail/Edit')
            },
            {
              path: 'submitEdit',
              name: '提交编辑页',
              component: () => import('@/pages/detail/SubmitEdit')
            }
          ]
        },
        // {
        //   path: 'intelligence',
        //   name: '威胁情报',
        //   meta: {
        //     icon: 'profile'
        //   },
        // },
        {
          path: 'kgraph',
          name: '漏洞图谱',
          meta: {
            icon: 'profile'
          },
          component: BlankView,
          children: [
            {
              path: 'test',
              name: 'Test'
            },
            {
              path: 'vedio',
              name: '视频监控类',
              component: () => import('@/pages/kg/Graph01')
            },
            {
              path: 'mobile',
              name: '移动设备类',
              component: () => import('@/pages/kg/Graph02')
            },
            {
              path: 'industry',
              name: '工业控制类',
              component: () => import('@/pages/kg/Graph03')
            },
            {
              path: 'smart',
              name: '智能家居类',
              component: () => import('@/pages/kg/Graph04')
            }
          ]
        },
        {
          path: 'userlist',
          name: '用户管理',
          meta: {
            icon: 'table',
            authority: {
              role: 'admin'
            }
          },
          // component: PageView,
          component: () => import('@/pages/list/UserList'),
        },
        {
          path: 'devicelist',
          name: '设备管理',
          meta: {
            icon: 'table',
            authority: {
              role: 'admin'
            }
          },
          // component: PageView,
          component: () => import('@/pages/device/DeviceList'),
        },
        {
          path: 'alertlist',
          name: '预警管理',
          meta: {
            icon: 'table',
            authority: {
              role: 'admin'
            }
          },
          // component: PageView,
          component: () => import('@/pages/list/AlertList2'),
        },
        {
          path: 'deviceedit',
          name: '设备信息编辑',
          component: () => import('@/pages/device/DeviceEdit'),
          meta: {
            invisible: true
          }
        },
        {
          path: 'about',
          name: '关于我们',
          meta: {
            icon: 'profile'
          }
        },
        {
          path: 'useredit',
          name: '用户编辑',
          component: () => import('@/pages/account/userEdit'),
          meta: {
            invisible: true
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
