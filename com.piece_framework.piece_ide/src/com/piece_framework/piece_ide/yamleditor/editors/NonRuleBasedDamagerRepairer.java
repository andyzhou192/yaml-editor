package com.piece_framework.piece_ide.yamleditor.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.presentation.IPresentationDamager;
import org.eclipse.jface.text.presentation.IPresentationRepairer;
import org.eclipse.jface.util.Assert;
import org.eclipse.swt.custom.StyleRange;

/**
 * ���[���Ȃ���{�_���[�W���^���y�A��.
 * 
 * @author Hideharu Matsufuji
 * @version 0.2.0
 * @since 0.2.0
 * @see org.eclipse.jface.text.rules.MultiLineRule
 * 
 */
public class NonRuleBasedDamagerRepairer
    implements IPresentationDamager, IPresentationRepairer {

    // �Ώۃh�L�������g
    private IDocument fDocument;
    // �f�t�H���g�̃e�L�X�g����
    private TextAttribute fDefaultTextAttribute;
    
    /**
     * �R���X�g���N�^.
     * 
     * @param defaultTextAttribute �f�t�H���g�̃e�L�X�g����
     */
    public NonRuleBasedDamagerRepairer(TextAttribute defaultTextAttribute) {
        Assert.isNotNull(defaultTextAttribute);
    
        fDefaultTextAttribute = defaultTextAttribute;
    }

    /**
     * �h�L�������g�ݒ�.
     * 
     * @param document �h�L�������g
     * @see org.eclipse.jface.text.presentation.IPresentationRepairer
     *          #setDocument(IDocument)
     */
    public void setDocument(IDocument document) {
        fDocument = document;
    }
    
    /**
     * �I�t�Z�b�g����̍s�̏I���ʒu��Ԃ�.
     *
     * @param offset �I�t�Z�b�g
     * @return �I�t�Z�b�g����̍s�̏I���ʒu
     * @exception BadLocationException �J�����g�h�L�������g�̈ʒu���s��
     */
    protected int endOfLineOf(int offset) throws BadLocationException {
        
        IRegion info = fDocument.getLineInformationOfOffset(offset);
        if (offset <= info.getOffset() + info.getLength()) {
            return info.getOffset() + info.getLength();
        }
        
        int line = fDocument.getLineOfOffset(offset);
        try {
            info = fDocument.getLineInformation(line + 1);
            return info.getOffset() + info.getLength();
        } catch (BadLocationException x) {
            return fDocument.getLength();
        }
    }

    /**
     * �_���[�W���y�A�����擾����.
     * 
     * @param partition �p�[�e�B�[�V����
     * @param event �C�x���g
     * @param documentPartitioningChanged �p�[�e�B�[�V�����ŕύX����������
     * @return �_���[�W���y�A��
     * @see org.eclipse.jface.text.presentation.IPresentationDamager
     *          #getDamageRegion(ITypedRegion, DocumentEvent, boolean)
     */
    public IRegion getDamageRegion(
        ITypedRegion partition,
        DocumentEvent event,
        boolean documentPartitioningChanged) {
        if (!documentPartitioningChanged) {
            try {
            
                IRegion info =
                    fDocument.getLineInformationOfOffset(event.getOffset());
                int start = Math.max(partition.getOffset(), info.getOffset());
            
                int end = event.getOffset();
                if (event.getText() == null) {
                    end += event.getLength();
                } else {
                    end += event.getText().length();
                }
                
                if (info.getOffset() <= end
                    && end <= info.getOffset() + info.getLength()) {
                    // optimize the case of the same line
                    end = info.getOffset() + info.getLength();
                } else {
                    end = endOfLineOf(end);
                }
                
                end =
                    Math.min(
                        partition.getOffset() + partition.getLength(),
                        end);
                return new Region(start, end - start);
            
            } catch (BadLocationException x) {
            }
        }
        
        return partition;
    }

    /**
     * �v���[���e�[�V�����𐶐�����.
     * 
     * @param presentation �e�L�X�g�v���[���e�[�V����
     * @param region ���[�W����
     * @see org.eclipse.jface.text.presentation.IPresentationRepairer
     *          #createPresentation(TextPresentation, ITypedRegion)
     */
    public void createPresentation(
        TextPresentation presentation,
        ITypedRegion region) {
        addRange(
            presentation,
            region.getOffset(),
            region.getLength(),
            fDefaultTextAttribute);
    }

    /**
     * �w�肳�ꂽ�e�L�X�g�v���[���e�[�V�����ɃX�^�C����ǉ�����.
     * 
     * @param presentation �e�L�X�g�v���[���e�[�V����
     * @param offset �I�t�Z�b�g
     * @param length �X�^�C����
     * @param attr �e�L�X�g����
     */
    protected void addRange(
        TextPresentation presentation,
        int offset,
        int length,
        TextAttribute attr) {
        if (attr != null) {
            presentation.addStyleRange(
                new StyleRange(
                    offset,
                    length,
                    attr.getForeground(),
                    attr.getBackground(),
                    attr.getStyle()));
        }
    }
}