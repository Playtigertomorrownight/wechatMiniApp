var menuList =	[
	{
		id: '1',
		name: '菜单管理',
		title: '菜单管理页面',
		icon: 'el-icon-menu',
		childList: [
            {
                id: '1-1',
                name: '后台菜单管理',
                title: '菜单管理-后台菜单管理',
                icon: 'el-icon-s-tools',
                url: 'back/menu',
            }
        ]
	},
	{
		id: '2',
		name: '用户-角色管理',
		title: '用户-角色管理页面',
		icon: 'el-icon-s-custom',
		childList: [
            {
                id: '2-1',
                name: '用户列表',
                title: '用户管理-用户列表',
                icon: 'el-icon-user-solid',
                url: 'back/user',
            }
        ]
	}
]