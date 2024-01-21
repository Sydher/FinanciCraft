export * from './account.service';
import { AccountService } from './account.service';
export * from './category.service';
import { CategoryService } from './category.service';
export * from './transaction.service';
import { TransactionService } from './transaction.service';
export const APIS = [AccountService, CategoryService, TransactionService];
