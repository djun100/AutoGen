package com.cy.util;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

public class UtilPlugin {

    public static Project getProject(AnActionEvent anActionEvent){
        Project fatherProject = anActionEvent.getProject();
        return fatherProject;
    }

    public static Editor getEditor(AnActionEvent anActionEvent){
        Editor editor = CommonDataKeys.EDITOR.getData(anActionEvent.getDataContext());
        return editor;
    }

    public static PsiFile getPsiFile(AnActionEvent anActionEvent){
        PsiFile file = anActionEvent.getData(PlatformDataKeys.PSI_FILE);
        return file;
    }

    public static String getSelectedText(AnActionEvent anActionEvent){
        String selectedText = getEditor(anActionEvent).getSelectionModel().getSelectedText();
        return selectedText;
    }

    public static PsiFile getFirstPsiFileByFileName(AnActionEvent anActionEvent,String fileName){
        Project project=getProject(anActionEvent);
        PsiFile[] foundFiles = FilenameIndex.getFilesByName(project, fileName, GlobalSearchScope.allScope(project));
        if (foundFiles.length <= 0) {
            return null;
        }
        return foundFiles[0];
    }

    public static String getProjectRootPath(AnActionEvent anActionEvent){
        String basePath = getProject(anActionEvent).getBasePath();
        return basePath;
    }

    public static String getCurrFilePath(AnActionEvent anActionEvent){
        String currFilePath= getPsiFile(anActionEvent).getVirtualFile().getPath();
        return currFilePath;
    }

    /**插件安装后，会被解压到插件沙箱
     * @param pluginId
     * @return C:\Users\xuechao.wang\.IdeaIC2018.3\system\plugins-sandbox\plugins\AutoGen for example.
     */
    public static String getPluginPath(String pluginId){
        IdeaPluginDescriptor plugin = PluginManager.getPlugin(PluginId.getId(pluginId));
        String pluginPath = plugin.getPath().getAbsolutePath();
        return pluginPath;
    }


}
