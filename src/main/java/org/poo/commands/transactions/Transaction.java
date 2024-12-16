package org.poo.commands.transactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.poo.utils.Utils;

import java.util.List;

public interface Transaction {
    ObjectNode toJson();
}
