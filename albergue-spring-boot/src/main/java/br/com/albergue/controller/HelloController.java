package br.com.albergue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@RequestMapping("/") //serve para mostrar o url quando o spring irá chamar esse método
	@ResponseBody //serve para para nao ter que navegar por 1 pagina, senao considerará que é uma pagina e procurará a mesma no projeto
	public String hello() {
		return "Hello World!";
	}
}
