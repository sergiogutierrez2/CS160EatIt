package application;

/**
 * TODO: Write a description of the class here.
 * 
 * @author Eat_It(Summer 2021 Team)
 */
public class RecipeStep 
{
	private String recipe_num;
	private String step_num;
	private String step_desc;
	
	public RecipeStep(String recipe_num, String step_num, String step_desc) 
	{
		this.recipe_num = recipe_num;
		this.step_num = step_num;
		this.step_desc = step_desc;
	}
	
	public String getRecipe_num() {
		return recipe_num;
	}
	
	public void setRecipe_num(String recipe_num) {
		this.recipe_num = recipe_num;
	}
	
	public String getStep_num() {
		return step_num;
	}
	
	public void setStep_num(String step_num) {
		this.step_num = step_num;
	}
	
	public String getStep_desc() {
		return step_desc;
	}
	
	public void setStep_desc(String step_desc) {
		this.step_desc = step_desc;
	}
	
	@Override
	public String toString()
	{
		return "Recipe_num: " + recipe_num +", Step_num: " + step_num + ", Step_desc: " + step_desc + "\n";
	}
}
