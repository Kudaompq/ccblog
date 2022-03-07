import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layout'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import getPageTitle from '@/utils/get-page-title'

Vue.use(VueRouter)

const routes = [
	{
		path: '/login',
		component: () => import('@/views/login/index'),
		meta: {title: "后台管理登录"},
		hidden: true
	},
	{
		path: '/404',
		component: () => import('@/views/404'),
		meta: {title: "页面无法找到"},
		hidden: true
	},
	{
		path: '/',
		component: Layout,
		redirect: '/dashboard',
		children: [
			{
				path: 'dashboard',
				name: 'Dashboard',
				component: () => import('@/views/dashboard/index'),
				meta: {title: 'Dashboard', icon: 'dashboard'}
			}
		]
	},
	{
		path: '/blog',
		component: Layout,
		redirect: '/blog/write',
		name: 'Blog',
		meta: {title: '博客管理', icon: 'el-icon-menu'},
		children: [
			{
				path: 'write',
				name: 'WriteBlog',
				component: () => import('@/views/blog/blog/BlogEdit'),
				meta: {title: '文章发布', icon: 'el-icon-edit'}
			},
			{
				path: 'list',
				name: 'BlogList',
				component: () => import('@/views/blog/blog/BlogList'),
				meta: {title: '文章管理', icon: 'el-icon-s-order'}
			},
			{
				path: 'edit/:id',
				name: 'BlogEdit',
				component: () => import('@/views/blog/blog/BlogEdit'),
				meta: {title: '文章编辑', icon: 'el-icon-edit'},
				hidden: true
			},
			{
				path: 'tag',
				name: 'TagList',
				component: () => import('@/views/blog/tag/TagList'),
				meta: {title: '标签管理',icon: 'tag'}
			},
			{
				path: 'category',
				name: 'CategoryList',
				component: () => import('@/views/blog/category/CategoryList'),
				meta: {title: '分类管理',icon: 'category'}
			},
			{
				path: 'moment',
				name: 'MomentList',
				component: () => import('@/views/blog/comment/CommentList'),
				meta: {title: '评论管理',icon: 'el-icon-s-comment'}
			}
		]
	},
	{
		path: '/moment',
		component: Layout,
		redirect: '/moment/list',
		name: 'Moment',
		meta: {title: '动态管理', icon: 'el-icon-s-claim'},
		children: [
			{
				path: 'write',
				name: 'MomentWrite',
				component: () => import('@/views/moment/MomentEdit'),
				meta: {title: '动态发布', icon: 'el-icon-edit'}
			},
			{
				path: 'list',
				name: 'MomentList',
				component: () => import('@/views/moment/MomentList'),
				meta: {title: '动态列表', icon: 'viewgallery'}
			},
			{
				path: 'edit/:id',
				name: 'MomentEdit',
				component: () => import('@/views/moment/MomentEdit'),
				meta: {title: '动态编辑', icon: 'el-icon-edit'},
				hidden: true
			}
		]
	},
	{
		path: '/page',
		name: 'page',
		redirect: '/page/site',
		component: Layout,
		meta: {title: '页面管理',icon: 'el-icon-document-copy'},
		children: [
			{
				path: 'site',
				name: 'SiteSetting',
				component: () => import('@/views/page/SiteSetting'),
				meta: {title: '站点设置',icon: 'bianjizhandian'}
			},
			{
				path: 'friend',
				name: 'FriendList',
				component: () => import('@/views/page/FriendList'),
				meta: {title: '友链管理', icon: 'friend'}
			},
			{
				path: 'about',
				name: 'About',
				component: () => import('@/views/page/About'),
				meta: {title: '关于我', icon: 'el-icon-tickets'}
			},
		]

	},
	{
		path:'/log',
		name: 'log',
		redirect: '/log/login',
		component: Layout,
		meta: {title: '日志管理',icon: 'el-icon-document'},
		children: [
			{
				path: 'login',
				name: 'LoginLog',
				component: () => import('@/views/log/LoginLog'),
				meta: {title: '登录日志',icon:'el-icon-finished'}
			},
			{
				path: 'operation',
				name: 'OperationLog',
				component: () => import('@/views/log/OperationLog'),
				meta: {title: '操作日志',icon:'el-icon-document-checked'}
			},
			{
				path: 'exception',
				name: 'ExceptionLog',
				component: () => import('@/views/log/ExceptionLog'),
				meta: {title: '异常日志',icon:'el-icon-document-delete'}
			},
			{
				path: 'visit',
				name: 'VisitLog',
				component: () => import('@/views/log/VisitLog'),
				meta: {title: '访问日志',icon:'el-icon-data-line'}
			},
		]
	},
	{
		path: '/statistics',
		name: 'Statistics',
		redirect: '/statistics/visitor',
		component:  Layout,
		meta: {title: '数据统计',icon: 'el-icon-s-data'},
		children: [
			{
				path: 'visitor',
				name: 'Visitor',
				component: () => import('@/views/statistics/Visitor'),
				meta: {title: '访客统计',icon: 'el-icon-s-marketing'}
			},
		]
	},

	// 404 page must be placed at the end !!!
	{path: '*', redirect: '/404', hidden: true}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

router.beforeEach(async (to, from, next) => {
	if (to.path !== '/login'){
		const token = window.localStorage.getItem(`token`)
		if (!token) return next("/login")
	}
	document.title = getPageTitle(to.meta.title)
	next()
})



export default router