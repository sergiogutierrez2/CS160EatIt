package application;

/**
 * Java Class that manages the steps of each of the recipes.
 * 
 * @author Eat_It(Summer 2021 Team)
 */
public class RecipeStep 
{
	private String recipe_num;
	private String step_num;
	private String step_desc;
	
	/**
	 * This is the constructor for the RecipeStep class that accepts
	 * a recipe number, step number, and step description.
	 * @param recipe_num Number of the recipe.
	 * @param step_num Number of the step.
	 * @param step_desc Step description.
	 */
	public RecipeStep(String recipe_num, String step_num, String step_desc) 
	{
		this.recipe_num = recipe_num;
		this.step_num = step_num;
		this.step_desc = step_desc;
	}
	
	/**
	 * This method returns the number of the recipe.
	 * @return The number of the recipe.
	 */
	public String getRecipe_num() {
		return recipe_num;
	}
	
	/**
	 * This method is used to set the recipe's number
	 * with the given parameter.
	 * @param recipe_num The number of the recipe.
	 */
	public void setRecipe_num(String recipe_num) {
		this.recipe_num = recipe_num;
	}
	
	/**
	 * This method returns the number of the step.
	 * @return The number of the step.
	 */
	public String getStep_num() {
		return step_num;
	}
	
	/**
	 * This method is used to set the recipe's step number
	 * with the given parameter.
	 * @param step_num The number of the step of the recipe.
	 */
	public void setStep_num(String step_num) {
		this.step_num = step_num;
	}
	
	/**
	 * This method returns the description of the step.
	 * @return The description of the step.
	 */
	public String getStep_desc() {
		return step_desc;
	}
	
	/**
	 * This method is used to set the recipe's step description
	 * with the given parameter.
	 * @param step_num The number of the step of the recipe.
	 */
	public void setStep_desc(String step_desc) {
		this.step_desc = step_desc;
	}
	
	/**
	 * Returns a string representation of the object.
	 * @return A string representation of the object.
	 */
	@Override
	public String toString()
	{
		return "Recipe_num: " + recipe_num +", Step_num: " + step_num + ", Step_desc: " + step_desc + "\n";
	}
}
