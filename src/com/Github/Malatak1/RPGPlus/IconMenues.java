public class IconMenues{
  private IconMenu baseIconMenu;
  private static initIconMenues(){
    baseIconMenu = new IconMenu("Customize Character:", 9, /*get other optionclickhandler, get plugin*/, null, null);
  }
  public static getBaseIconMenu(){
    if(baseIconMenu == null) initIconMenues;
    return baseIconMenu;
  }
}
