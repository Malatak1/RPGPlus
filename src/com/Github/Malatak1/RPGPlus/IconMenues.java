public class IconMenues{
  private IconMenu baseIconMenu;
  private static initIconMenues(){
    baseIconMenu = new IconMenu("Customize Character:", 9, null, RPGPlus.inst());
  }
  public static getBaseIconMenu(){
    if(baseIconMenu == null) initIconMenues;
    return baseIconMenu;
  }
}
