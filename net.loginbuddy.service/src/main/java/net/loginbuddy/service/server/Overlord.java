package net.loginbuddy.service.server;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import net.loginbuddy.service.config.LoginbuddyConfig;

public class Overlord extends HttpServlet {

  private static Logger LOGGER = Logger.getLogger(String.valueOf(Overlord.class));

  @Override
  public void init() throws ServletException {
    super.init();
    // initialize the configuration. If this fails, there is no reason to continue
    if (LoginbuddyConfig.getInstance().isConfigured()) {
      LOGGER.info("Loginbuddy successfully started!");
    } else {
      LOGGER.severe("Stopping loginbuddy since its configuration could not be loaded! Fix that first!");
      System.exit(1);
    }
  }

  void notYetImplemented(HttpServletResponse response) throws IOException {
    response.setStatus(418);
    response.setContentType("application/json");
    response.getWriter().write("{\"sorry\":\"not yet implemented\"}");
  }
}