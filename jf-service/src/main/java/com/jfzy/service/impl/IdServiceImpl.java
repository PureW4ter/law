package com.jfzy.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfzy.service.IdService;
import com.jfzy.service.po.IdPo;
import com.jfzy.service.repository.IdRepository;

@Service
public class IdServiceImpl implements IdService {

	private static final Logger logger = LoggerFactory.getLogger(IdServiceImpl.class);

	@Autowired
	private IdRepository idRepo;

	public void init(int type, int page) {
		List<IdPo> pos = idRepo.findByTypeAndPage(type, page);
		if (pos == null || pos.size() <= 0) {
			IdPo po = new IdPo();
			po = new IdPo();
			po.setType(type);
			po.setPage(page);
			po.setCurrentId(1000);
			idRepo.save(po);
		}
	}

	@Override
	public int generateId(int type, int page) {
		List<IdPo> pos = idRepo.findByTypeAndPage(type, page);
		IdPo po = null;
		if (pos == null || pos.size() <= 0) {
			logger.error("Generate id failed:");
			return -1;
		} else {
			po = pos.get(0);
		}

		if (po != null) {
			int index = 20;
			for (; index > 0; --index) {
				int updated = idRepo.updateCurrentId(po.getId(), po.getCurrentId());
				if (updated > 0) {
					return po.getCurrentId() + 1;
				} else {
					po = idRepo.findOne(po.getId());
				}
			}

			logger.error("Failed in generate code, times up");
		}

		return -1;
	}

}
