import { Component, OnInit, } from '@angular/core';
import { APP_CONSTANTS } from '../../../app.constants';
import { TreeNode } from '../../models/tree-node.model';

@Component({
    selector: 'app-tree-menu',
    templateUrl: './tree-menu.view.component.html',
    styleUrls: ['./tree-menu.style.component.css']
})
export class TreeMenuComponent implements OnInit {

    public nodes: TreeNode[];

    constructor() {
    }

    ngOnInit() {
        this.loadMenus();
    }

    private loadMenus() {
        const rootNode = new TreeNode('Registry', APP_CONSTANTS.routes.home, true, 'root_open.gif');

        const node = new TreeNode('Cadastros', '', false, 'folder.gif');
        node.addChildren(new TreeNode('Estado Civil', APP_CONSTANTS.routes.maritalStatus, false, 'page.gif'));
        node.addChildren(new TreeNode('Reça', APP_CONSTANTS.routes.breed, false, 'page.gif'));
        node.addChildren(new TreeNode('Tipo de Telefone', APP_CONSTANTS.routes.phoneType, false, 'page.gif'));
        rootNode.addChildren(node);

        rootNode.addChildren(new TreeNode('Pessoa', APP_CONSTANTS.routes.searchPerson, false, 'page.gif'));

        // logout adds separately
        rootNode.addChildren(new TreeNode('Sair', APP_CONSTANTS.routes.logout, false, 'exit.gif'));
        this.nodes = [rootNode];

        console.log(this.nodes);
    }

}
