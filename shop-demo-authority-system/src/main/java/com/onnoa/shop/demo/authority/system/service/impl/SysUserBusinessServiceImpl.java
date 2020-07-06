package com.onnoa.shop.demo.authority.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onnoa.shop.demo.authority.system.domain.SysUserBusiness;
import com.onnoa.shop.demo.authority.system.mapper.SysUserBusinessMapper;
import com.onnoa.shop.demo.authority.system.service.SysUserBusinessService;
@Service
public class SysUserBusinessServiceImpl extends ServiceImpl<SysUserBusinessMapper, SysUserBusiness> implements SysUserBusinessService{

}
