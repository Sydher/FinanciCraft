import { FormBuilder, FormGroup } from "@angular/forms";
import { ConfirmationService, MessageService } from "primeng/api";

export abstract class AbstractCrudComponent<T = any> {

    protected formCrud: FormGroup;
    protected showFormModal: boolean;
    protected items: T[];
    protected selectedItems!: T[] | null;

    constructor(protected formBuilder: FormBuilder, protected confirmationService: ConfirmationService, protected messageService: MessageService) {
        this.formCrud = this.initForm();
        this.showFormModal = false;
        this.items = [];
    }

    /**
     * Init add/edit form.
     * @param item item to update (null if create)
     */
    protected abstract initForm(item?: T): FormGroup;

    /**
     * Refresh items list with backend data.
     */
    protected abstract refreshItemsList(): void;

    /**
     * Add a new item.
     */
    protected abstract addItem(): void;

    /**
     * Edit an item.
     */
    protected abstract editItem(): void;

    /**
     * Delete an item.
     * @param item the item to delete
     */
    protected abstract deleteItem(item: T): void;

    /**
     * Delete all selected items.
     */
    protected abstract deleteSelectedItems(): void;

    /**
     * Open add/edit modal.
     * @param item item to update (null if create)
     */
    protected openModal(item?: T): void {
        this.formCrud = this.initForm(item);
        this.showFormModal = true;
    }

    /**
     * Close add/edit modal.
     */
    protected closeModal(): void {
        this.refreshItemsList();
        this.showFormModal = false;
    }

}
