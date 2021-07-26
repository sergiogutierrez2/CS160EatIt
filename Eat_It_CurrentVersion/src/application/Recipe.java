package application;

public class Recipe 
{
    
    private String recipe_num;
    private String recipe_name;
    private String cook_time; //a number and then a unit (e.g. 30 min)
    private String prep_time; //a number and then a unit (e.g. 30 min)
    private String executable; //"0" for false, "1" for true
    private InventoryList iList;
    private int iListSize;
    public InventoryList missingList;
    public Recipe(String recipe_num, String recipe_name, 
                    String cook_time, String prep_time, 
                                        String executable) 
    {
        this.recipe_num = recipe_num;
        this.recipe_name = recipe_name;
        this.cook_time = cook_time;
        this.prep_time = prep_time;
        this.executable = executable;
        iList = new InventoryList();
        missingList = new InventoryList();
    }
    
    
    
    public Recipe(String recipe_num, String recipe_name, 
            String cook_time, String prep_time) 
    {
        this.recipe_num = recipe_num;
        this.recipe_name = recipe_name;
        this.cook_time = cook_time;
        this.prep_time = prep_time;
        iList = new InventoryList();
    }
    
    public String getRecipe_num() {
        return recipe_num;
    }

    public void setRecipe_num(String recipe_num) {
        this.recipe_num = recipe_num;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getCook_time() {
        return cook_time;
    }

    public void setCook_time(String cook_time) {
        this.cook_time = cook_time;
    }

    public String getPrep_time() {
        return prep_time;
    }

    public void setPrep_time(String prep_time) {
        this.prep_time = prep_time;
    }

    public String getExecutable() {
        return executable;
    }
    
    public InventoryList getIList()
    {
        return iList;
    }
    
    public void setIList(InventoryList iList)
    {
        this.iList = iList;
    }

    public void setExecutable(String executable) {
        
        if(!(executable.equals("0")) && !(executable.equals("1")) )
        {
            System.out.println("Invalid executable input: " + executable + ", is not '0' or '1'");
            this.executable = "0";
            
        }
        else
        {
            this.executable = executable;
        }
    }

    public boolean isExecutable()
    {
        return (this.executable.equals("1"));
    }
    
    public void addItemToRecipe(RecipeItem i)
    {
        iList.addItem(i);
    }
    public void addItemToMissingList(RecipeItem i)
    {
        missingList.addItem(i);
    }
    
    public void removeItemFromRecipe(String item_num, String item_name)
    {
        iList.removeItem(item_num, item_name);
    } 
    
    public void printIList()
    {
        System.out.print(recipe_name + " Recipe:\n" 
                            + iList.toString());
    }
    
    public int getIListSize()
    {
        return iList.getSize();
    }
    
    @Override
    public String toString()
    {
        String s = "";
        s += "[";
        s += this.getRecipe_num() + ", ";
        s += this.getRecipe_name() + ", ";
        s += this.getPrep_time() + ", ";
        s += this.getCook_time() + ", ";
        s += this.getExecutable() + "]";
        
        return s;
    }

}