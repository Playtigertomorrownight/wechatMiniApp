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
                childList: [
                    {
                        id: '1-1-1',
                        name: '后台菜单管理1',
                        title: '菜单管理-后台菜单管理1',
                        icon: 'el-icon-user',
                        url: 'back/menu',
                        childList: [
                            {
                                id: '1-1-1-1',
                                name: '后台菜单管理1-1',
                                title: '菜单管理-后台菜单管理1-1',
                                icon: 'el-icon-star-off',
                                url: 'back/menu',
                            }
                        ]
                    }
                ]
            }
        ]
	}
]