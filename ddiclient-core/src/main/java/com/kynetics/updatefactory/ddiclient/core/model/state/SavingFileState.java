/*
 * Copyright © 2017-2018 Kynetics LLC
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 */

package com.kynetics.updatefactory.ddiclient.core.model.state;

import com.kynetics.updatefactory.ddiclient.core.model.FileInfo;
import com.kynetics.updatefactory.ddiclient.core.model.Hash;
import com.kynetics.updatefactory.ddiclient.core.model.event.AbstractEvent;
import com.kynetics.updatefactory.ddiclient.core.model.event.FileCorruptedEvent;

import java.io.InputStream;
import java.util.List;

import static com.kynetics.updatefactory.ddiclient.core.model.state.AbstractState.StateName.SAVING_FILE;

/**
 * @author Daniele Sergio
 */
public class SavingFileState extends AbstractStateWithFile {
    private static final long serialVersionUID = -4330529885840336590L;

    public SavingFileState(Long actionId, boolean isForced, List<FileInfo> fileInfoList, int nextFileToDownload, Hash lastHash, InputStream inputStream) {
        super(SAVING_FILE, actionId, isForced, fileInfoList, nextFileToDownload, lastHash);
        this.inputStream = inputStream;
    }

    @Override
    public AbstractState onEvent(AbstractEvent event) {
        switch (event.getEventName()) {
            case SUCCESS:
                return getSize() - 1 == getNextFileToDownload() ?
                        new UpdateReadyState(getActionId(), isForced()) :
                        new UpdateDownloadState(getActionId(), isForced(), getFileInfoList(), getNextFileToDownload() + 1);
            case FILE_CORRUPTED:
                final FileCorruptedEvent corruptedEvent = (FileCorruptedEvent) event;
                final Hash currentHash = corruptedEvent.getDownloadedFileHash();
                return currentHash == null || getLastHash() == null || !currentHash.equals(getLastHash()) ?
                        new UpdateDownloadState(getActionId(),
                                isForced(),
                                getFileInfoList(),
                                getNextFileToDownload(),
                                currentHash) :
                        new ServerFileCorruptedState(getActionId());

            default:
                return super.onEvent(event);
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    transient private final InputStream inputStream;
}
