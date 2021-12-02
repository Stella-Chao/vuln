import {users, groups} from './index'

const events = [
  {
    type: 0,
    event: '八月迭代'
  },
  {
    type: 1,
    event: '留言'
  },
  {
    type: 2,
    event: '项目进展'
  }
]

const activities = users.map((user, index) => {
  return {
    user: Object.assign({}, user, {group: groups[user.groupId]}),
    activity: events[index % events.length],
    template: ''
  }
})

const templates = [
  // (user, activity) => { return `${user.name} 在 <a >${user.group}</a> 上报了漏洞 <a>${activity.event}</a>` },
  (user) => { return `${user.name} 提交了漏洞...` },
  (user) => { return `${user.name} 提交了漏洞...` },
  (user) => { return `${user.name} 核实了漏洞...` }
]

export {activities, templates}
