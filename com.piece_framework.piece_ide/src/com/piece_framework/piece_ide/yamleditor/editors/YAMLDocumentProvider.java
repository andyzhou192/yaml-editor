package com.piece_framework.piece_ide.yamleditor.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

/**
 * YAML �h�L�������g�v���o�C�_�[.
 * �h�L�������g�̐����E�Ǘ����s���B
 * 
 * @author Hideharu Matsufuji
 * @version 0.2.0
 * @since 0.2.0
 * @see org.eclipse.ui.editors.text.FileDocumentProvider
 * 
 */
public class YAMLDocumentProvider extends FileDocumentProvider {

    /**
     * �h�L�������g�𐶐�����.
     * 
     * @param element �h�L�������g�̌��ɂȂ�v�f
     * @return �h�L�������g
     * @throws CoreException ���ʗ�O
     * @see org.eclipse.ui.editors.text.StorageDocumentProvider
     *          #createDocument(java.lang.Object)
     */
    protected IDocument createDocument(Object element) throws CoreException {
        IDocument document = super.createDocument(element);
        
        if (document != null) {
            // �h�L�������g���Ӗ��̂�����ɕ�������
            IDocumentPartitioner partitioner =
                new FastPartitioner(
                    new YAMLPartitionScanner(),
                    new String[] {
                        YAMLPartitionScanner.YAML_COMMENT,
                        YAMLPartitionScanner.YAML_MAPPING_KEY,
                        YAMLPartitionScanner.YAML_MAPPING_VAL}
                    );
            
            partitioner.connect(document);
            document.setDocumentPartitioner(partitioner);
        }
        return document;
    }
}