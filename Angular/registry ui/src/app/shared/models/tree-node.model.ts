/**
 * Object thats represents a node in the tree menu.
 */
export class TreeNode {
    name: string;
    link: string;
    icon: string;
    isExpanded: boolean;
    children: TreeNode[];

    constructor(name: string, link: string, isExpanded = false, iconName = '') {
        this.name = name;
        this.link = link;
        this.isExpanded = isExpanded;
        this.icon = iconName;
        this.children = [];
    }

    public addChildren(child: TreeNode): void {
        this.children.push(child);
    }

    public setIcon(iconName: string): void {
        this.icon = iconName;
    }
}
