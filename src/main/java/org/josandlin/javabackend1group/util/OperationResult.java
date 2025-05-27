package org.josandlin.javabackend1group.util;

import org.josandlin.javabackend1group.dto.AbstractDTO;

import java.util.Optional;

public class OperationResult<T extends AbstractDTO> {
    private final ActualResult result;
    private final String resultMessage;
    private final T resultObject;

    public OperationResult(ActualResult result, String resultMessage, T resultObject) {
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

    public Optional<T> getResultObject() {
        return Optional.ofNullable(resultObject);
    }
}
