package com.piece_framework.piece_ide;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * 
 * ���s�N���X
 *
 */
public class PieceAction implements IWorkbenchWindowActionDelegate {


    /**
     * �I������
     *
     */
    public void dispose() {
        // TODO �����������ꂽ���\�b�h�E�X�^�u

    }

    /**
     * ��������
     * @param window the window that provides the context for this delegate
     */
    public void init(IWorkbenchWindow window) {
        // TODO �����������ꂽ���\�b�h�E�X�^�u

    }

    /**
     * ���s����
     * @param action the action proxy that handles the presentation portion
     *      of the action
     */
    public void run(IAction action) {
        // ���b�Z�[�W�_�C�A���O�\��
        MessageDialog.openInformation(null, null, "��������Piece-ide���n�܂�܂��I");

    }

    /**
     * �f���Q�[�g������
     * @param action the action proxy that handles presentation portion of
     * 		the action
     * @param selection the current selection, or <code>null</code> if there
     * 		is no selection.
     */
    public void selectionChanged(IAction action, ISelection selection) {
        // TODO �����������ꂽ���\�b�h�E�X�^�u

    }

}
