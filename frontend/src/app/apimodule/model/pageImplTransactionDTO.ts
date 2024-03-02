/**
 * OpenAPI definition
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { PageableObject } from './pageableObject';
import { SortObject } from './sortObject';
import { TransactionDTO } from './transactionDTO';

export interface PageImplTransactionDTO { 
    content?: Array<TransactionDTO>;
    pageable?: PageableObject;
    last?: boolean;
    totalPages?: number;
    totalElements?: number;
    size?: number;
    number?: number;
    sort?: SortObject;
    first?: boolean;
    numberOfElements?: number;
    empty?: boolean;
}