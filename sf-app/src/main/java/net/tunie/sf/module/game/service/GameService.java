package net.tunie.sf.module.game.service;

import jakarta.annotation.Resource;
import net.tunie.sf.module.game.domain.dao.GameDao;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Resource
    private GameDao gameDao;
}
