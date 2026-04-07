package com.flower.d2c.service.impl;

import com.flower.d2c.model.Web2Block;
import com.flower.d2c.repository.Web2BlockRepository;
import com.flower.d2c.service.Web2BlockService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Web2BlockServiceImpl implements Web2BlockService {

    @Resource
    private Web2BlockRepository web2BlockRepository;

    @Override
    public List<Web2Block> findAllBlockWeb() {
        return web2BlockRepository.findAll();
    }

    @Override
    public Boolean deleteBlock(Long id) {
        web2BlockRepository.findById(id).ifPresent(web2Block -> web2BlockRepository.delete(web2Block));
        return true;
    }
}
