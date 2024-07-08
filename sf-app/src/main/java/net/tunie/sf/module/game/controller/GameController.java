package net.tunie.sf.module.game.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.module.game.service.GameService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @Resource
    private GameService gameService;
}
