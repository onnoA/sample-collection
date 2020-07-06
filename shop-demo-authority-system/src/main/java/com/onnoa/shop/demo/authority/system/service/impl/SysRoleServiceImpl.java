package com.onnoa.shop.demo.authority.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onnoa.shop.demo.authority.system.domain.SysRole;
import com.onnoa.shop.demo.authority.system.mapper.SysRoleMapper;
import com.onnoa.shop.demo.authority.system.service.SysRoleService;
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

}
