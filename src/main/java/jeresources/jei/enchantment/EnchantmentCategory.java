package jeresources.jei.enchantment;

import jeresources.jei.BlankJEIRecipeCategory;
import jeresources.jei.JEIConfig;
import jeresources.reference.Resources;
import jeresources.util.TranslationHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;

import javax.annotation.Nonnull;

public class EnchantmentCategory extends BlankJEIRecipeCategory<EnchantmentWrapper> {
    private static final int ITEM_X = 12;
    private static final int ITEM_Y = 11;

    public EnchantmentCategory() {
        super(JEIConfig.getJeiHelpers().getGuiHelper().createDrawable(Resources.Gui.Jei.TABS, 32, 0, 16, 16));
    }

    @Nonnull
    @Override
    public String getUid() {
        return JEIConfig.ENCHANTMENT;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return TranslationHelper.translateToLocal("jer.enchantments.title");
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return Resources.Gui.Jei.ENCHANTMENT;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull EnchantmentWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, ITEM_X, ITEM_Y);

        recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
    }
}
