# SSM-OA
SSM框架实现的OA系统

<p align="center">
   <a target="_blank" >
      <img src="https://img.shields.io/hexpm/l/plug.svg"/>
      <img src="https://img.shields.io/badge/JDK-1.8+-green.svg"/>
      <img src="https://img.shields.io/badge/SpringBoot-2.5.6.RELEASE-red"/>
      <img src="https://img.shields.io/badge/Vue-2.5.17-red"/>
      <img src="https://img.shields.io/badge/MySQL-5.7-green"/>
      <br/>
      <img src="https://img.shields.io/badge/Mybatis--Plus-3.4.1-red"/>
      <img src="https://img.shields.io/badge/druid-1.2.6-green"/>
      <img src="https://img.shields.io/badge/fastjson-2.0.21-green"/>
      <img src="https://img.shields.io/badge/knife4j-3.0.3-blue"/>
      <img src="https://img.shields.io/badge/knife4j-3.0.3-green"/>
   </a>
</p>
## 用户管理CRUD

```java
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService service;

    //用户条件分页查询
    @ApiOperation("用户条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit,
                        SysUserQueryVo sysUserQueryVo) {
        //创建page对象
        Page<SysUser> pageParam = new Page<>(page,limit);

        //封装条件，判断条件值不为空
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        //获取条件值
        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();
        //判断条件值不为空
        //like 模糊查询
        if(!StringUtils.isEmpty(username)) {
            wrapper.like(SysUser::getUsername,username);
        }
        //ge 大于等于
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge(SysUser::getCreateTime,createTimeBegin);
        }
        //le 小于等于
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le(SysUser::getCreateTime,createTimeEnd);
        }

        //调用mp的方法实现条件分页查询
        IPage<SysUser> pageModel = service.page(pageParam, wrapper);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysUser user = service.getById(id);
        return Result.ok(user);
    }

    @ApiOperation(value = "保存用户")
    @PostMapping("save")
    public Result save(@RequestBody SysUser user) {
        service.save(user);
        return Result.ok();
    }

    @ApiOperation(value = "更新用户")
    @PutMapping("update")
    public Result updateById(@RequestBody SysUser user) {
        service.updateById(user);
        return Result.ok();
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        service.removeById(id);
        return Result.ok();
    }
}
```

## 用户分配角色

SysRoleController

```java
@ApiOperation(value = "根据用户获取角色数据")
@GetMapping("/toAssign/{userId}")
public Result toAssign(@PathVariable Long userId) {
    Map<String, Object> roleMap = sysRoleService.findRoleByAdminId(userId);
    return Result.ok(roleMap);
}

@ApiOperation(value = "根据用户分配角色")
@PostMapping("/doAssign")
public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
    sysRoleService.doAssign(assginRoleVo);
    return Result.ok();
}
```

SysRoleServiceImpl

```java
@Autowired
private SysUserRoleMapper sysUserRoleMapper;

@Override
public Map<String, Object> findRoleByUserId(Long userId) {
    //查询所有的角色
    List<SysRole> allRolesList = this.list();

    //拥有的角色id
    List<SysUserRole> existUserRoleList = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId).select(SysUserRole::getRoleId));
    List<Long> existRoleIdList = existUserRoleList.stream().map(c->c.getRoleId()).collect(Collectors.toList());

    //对角色进行分类
    List<SysRole> assginRoleList = new ArrayList<>();
    for (SysRole role : allRolesList) {
        //已分配
        if(existRoleIdList.contains(role.getId())) {
            assginRoleList.add(role);
        }
    }

    Map<String, Object> roleMap = new HashMap<>();
    roleMap.put("assginRoleList", assginRoleList);
    roleMap.put("allRolesList", allRolesList);
    return roleMap;
}

@Transactional
@Override
public void doAssign(AssginRoleVo assginRoleVo) {
    sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, assginRoleVo.getUserId()));

    for(Long roleId : assginRoleVo.getRoleIdList()) {
        if(StringUtils.isEmpty(roleId)) continue;
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(assginRoleVo.getUserId());
        userRole.setRoleId(roleId);
        sysUserRoleMapper.insert(userRole);
    }
}
```

## 更改用户状态

SysUserController

```java
@ApiOperation(value = "更新状态")
@GetMapping("updateStatus/{id}/{status}")
public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
    sysUserService.updateStatus(id, status);
    return Result.ok();
}
```

SysUserServiceImpl



```java
@Transactional
@Override
public void updateStatus(Long id, Integer status) {
   SysUser sysUser = this.getById(id);
   if(status.intValue() == 1) {
      sysUser.setStatus(status);
   } else {
      sysUser.setStatus(0);
   }
   this.updateById(sysUser);
}
```

## 菜单管理CRUD

SysMenuController

```java
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "获取菜单")
    @GetMapping("findNodes")
    public Result findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu permission) {
        sysMenuService.save(permission);
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody SysMenu permission) {
        sysMenuService.updateById(permission);
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        sysMenuService.removeById(id);
        return Result.ok();
    }

}
```

## 给角色分配权限

SysMenuController

```java
@ApiOperation(value = "根据角色获取菜单")
@GetMapping("toAssign/{roleId}")
public Result toAssign(@PathVariable Long roleId) {
    List<SysMenu> list = sysMenuService.findSysMenuByRoleId(roleId);
    return Result.ok(list);
}

@ApiOperation(value = "给角色分配权限")
@PostMapping("/doAssign")
public Result doAssign(@RequestBody AssignMenuVo assignMenuVo) {
    sysMenuService.doAssign(assignMenuVo);
    return Result.ok();
}
```

SysMenuServiceImpl

```java
@Override
public List<SysMenu> findSysMenuByRoleId(Long roleId) {
    //全部权限列表
    List<SysMenu> allSysMenuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));

    //根据角色id获取角色权限
    List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
    //转换给角色id与角色权限对应Map对象
    List<Long> menuIdList = sysRoleMenuList.stream().map(e -> e.getMenuId()).collect(Collectors.toList());

    allSysMenuList.forEach(permission -> {
        if (menuIdList.contains(permission.getId())) {
            permission.setSelect(true);
        } else {
            permission.setSelect(false);
        }
    });

    List<SysMenu> sysMenuList = MenuHelper.buildTree(allSysMenuList);
    return sysMenuList;
}

@Transactional
@Override
public void doAssign(AssginMenuVo assginMenuVo) {
    sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, assginMenuVo.getRoleId()));

    for (Long menuId : assginMenuVo.getMenuIdList()) {
        if (StringUtils.isEmpty(menuId)) continue;
        SysRoleMenu rolePermission = new SysRoleMenu();
        rolePermission.setRoleId(assginMenuVo.getRoleId());
        rolePermission.setMenuId(menuId);
        sysRoleMenuMapper.insert(rolePermission);
    }
}
```

## 菜单管理CRUD

```java
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "获取菜单")
    @GetMapping("findNodes")
    public Result findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu permission) {
        sysMenuService.save(permission);
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody SysMenu permission) {
        sysMenuService.updateById(permission);
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        sysMenuService.removeById(id);
        return Result.ok();
    }

}
```

## 角色分配权限

SysMenuController

```java
@ApiOperation(value = "根据角色获取菜单")
@GetMapping("toAssign/{roleId}")
public Result toAssign(@PathVariable Long roleId) {
    List<SysMenu> list = sysMenuService.findSysMenuByRoleId(roleId);
    return Result.ok(list);
}

@ApiOperation(value = "给角色分配权限")
@PostMapping("/doAssign")
public Result doAssign(@RequestBody AssignMenuVo assignMenuVo) {
    sysMenuService.doAssign(assignMenuVo);
    return Result.ok();
}
```

SysMenuServiceImpl

```java
@Override
public List<SysMenu> findSysMenuByRoleId(Long roleId) {
    //全部权限列表
    List<SysMenu> allSysMenuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));

    //根据角色id获取角色权限
    List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
    //转换给角色id与角色权限对应Map对象
    List<Long> menuIdList = sysRoleMenuList.stream().map(e -> e.getMenuId()).collect(Collectors.toList());

    allSysMenuList.forEach(permission -> {
        if (menuIdList.contains(permission.getId())) {
            permission.setSelect(true);
        } else {
            permission.setSelect(false);
        }
    });

    List<SysMenu> sysMenuList = MenuHelper.buildTree(allSysMenuList);
    return sysMenuList;
}

@Transactional
@Override
public void doAssign(AssginMenuVo assginMenuVo) {
    sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, assginMenuVo.getRoleId()));

    for (Long menuId : assginMenuVo.getMenuIdList()) {
        if (StringUtils.isEmpty(menuId)) continue;
        SysRoleMenu rolePermission = new SysRoleMenu();
        rolePermission.setRoleId(assginMenuVo.getRoleId());
        rolePermission.setMenuId(menuId);
        sysRoleMenuMapper.insert(rolePermission);
    }
}
```

