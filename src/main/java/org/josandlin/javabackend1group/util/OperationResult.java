package org.josandlin.javabackend1group.util;

import org.josandlin.javabackend1group.dto.AbstractDTO;

import java.util.Optional;

public class OperationResult {
    private final ActualResult result;
    private final String resultMessage;
    private final AbstractDTO resultObject;

    public OperationResult(ActualResult result, String resultMessage, AbstractDTO resultObject) {
        this.result = result;
        this.resultMessage = resultMessage;
        this.resultObject = resultObject;
    }

    public ActualResult getResult() {
        return result;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public AbstractDTO getResultObject() {
        return resultObject;
    }
}
