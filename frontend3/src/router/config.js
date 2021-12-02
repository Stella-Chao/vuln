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
          // children: [
          //   // {
          //   //   path: 'workplace',
          //   //   name: '工作台',
          //   //   meta: {
          //   //     page: {
          //   //       closable: false
          //   //     }
          //   //   },
          //   //   component: () => import('@/pages/dashboard/workplace'),
          //   // },
          //   {
          //     path: 'analysis',
          //     name: '分析页',
          //     component: () => import('@/pages/dashboard/analysis'),
          //   }
          // ]
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
          // children: [
          //   {
          //     path: 'basic',
          //     name: '提交页面',
          //     component: () => import('@/pages/form/basic'),
          //   },
          //   // {
          //   //   path: 'step',
          //   //   name: '分步表单',
          //   //   component: () => import('@/pages/form/step'),
          //   // },
          //   // {
          //   //   path: 'advance',
          //   //   name: '高级表单',
          //   //   component: () => import('@/pages/form/advance'),
          //   // }
          // ]
        },
        {
          path: 'list',
          name: '漏洞列表',
          meta: {
            icon: 'table'
          },
          // component: PageView,
          component: () => import('@/pages/list/QueryList'),
          // children: [
          //   {
          //     path: 'query',
          //     name: '查询表格',
          //     meta: {
          //       authority: 'queryForm',
          //     },
          //     component: () => import('@/pages/list/QueryList'),
          //   },
          //   // {
          //   //   path: 'query/detail/:id',
          //   //   name: '查询详情',
          //   //   meta: {
          //   //     highlight: '/list/query',
          //   //     invisible: true
          //   //   },
          //   //   component: () => import('@/pages/Demo')
          //   // },
          //   // {
          //   //   path: 'primary',
          //   //   name: '标准列表',
          //   //   component: () => import('@/pages/list/StandardList'),
          //   // },
          //   // {
          //   //   path: 'card',
          //   //   name: '卡片列表',
          //   //   component: () => import('@/pages/list/CardList'),
          //   // },
          //   // {
          //   //   path: 'search',
          //   //   name: '搜索列表',
          //   //   component: () => import('@/pages/list/search/SearchLayout'),
          //   //   children: [
          //   //     {
          //   //       path: 'article',
          //   //       name: '文章',
          //   //       component: () => import('@/pages/list/search/ArticleList'),
          //   //     },
          //   //     {
          //   //       path: 'application',
          //   //       name: '应用',
          //   //       component: () => import('@/pages/list/search/ApplicationList'),
          //   //     },
          //   //     {
          //   //       path: 'project',
          //   //       name: '项目',
          //   //       component: () => import('@/pages/list/search/ProjectList'),
          //   //     }
          //   //   ]
          //   // }
          // ]
        },
        {
          path: 'details',
          name: '漏洞详情',
          meta: {
            icon: 'profile'
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
            // {
            //   path: 'advance',
            //   name: '高级详情页',
            //   component: () => import('@/pages/detail/AdvancedDetail')
            // }
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
  ]
}

export default options
