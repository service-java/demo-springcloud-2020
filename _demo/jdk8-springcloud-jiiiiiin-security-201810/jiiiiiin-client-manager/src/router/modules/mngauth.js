import layoutHeaderAside from '@/layout/header-aside'

const meta = { requiresAuth: true }

export default {
  path: '/mngauth',
  name: 'mngauth',
  meta,
  component: layoutHeaderAside,
  children: (pre => [
    { path: 'admin', name: `${pre}admin`, component: () => import('@/pages/mngauth/admin'), meta: { meta, title: '操作员管理' } },
    { path: 'role', name: `${pre}role`, component: () => import('@/pages/mngauth/role'), meta: { meta, title: '角色管理' } },
    { path: 'resource', name: `${pre}resource`, component: () => import('@/pages/mngauth/resource'), meta: { meta, title: '资源管理' } }
  ])('mngauth-')
}
