import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { COMM_REGISTRY_CONSTANTS } from './comm-registry.constants';
import { environment } from '../config/environments/environment';
import { PersonInfo } from '..//models/person-info.model';

@Component({
  selector: 'lib-comm-registry',
  templateUrl: './comm-registry.view.component.html',
  styleUrls: ['./comm-registry.style.scss']
})
export class CommRegistryComponent implements OnInit {

  public constants = COMM_REGISTRY_CONSTANTS;

  /**
   * Input for environment to point.
   */
  @Input() env: string;

  /**
   * Output containing person created.
   */
  @Output() person = new EventEmitter<PersonInfo>();

  public titleMessage = '';
  public envName: string;

  public personId: string;

  /**
   * Flag indicating if the confirmation page must be rendered.
   */
  public showConfirmation: boolean;

  ngOnInit() {
    console.log(this.env);

    this.envName = 'Ambiente ';
    if (this.env === 'production' || this.env === 'prod') {
      this.envName = '';
      environment.environmentHost = 'http://localhost:8080/';
    } else if (this.env === 'dev' || this.env === 'dsv') {
      this.envName = this.envName.concat('Desenvolvimento');
      environment.environmentHost = 'http://localhost:8080/';
    } else if (this.env === 'hmg' || this.env === 'homolog') {
      this.envName = this.envName.concat('Homologação');
      environment.environmentHost = 'http://localhost:8080/';
    } else {
      this.envName = this.envName.concat('Local');
      environment.environmentHost = 'http://localhost:8080/';
    }

    this.titleMessage = this.constants.titleMsg;
    this.showConfirmation = false;
  }

  /**
   * Renders form.
   */
  public backToForm(): void {
    this.showConfirmation = false;
  }

  /**
   * Emits the person created for the application who calls this component.
   */
  public emitPerson(personCreated: PersonInfo): void {

    // resets flags to return to form screen
    this.showConfirmation = false;
    this.person.emit(personCreated);
  }

  /**
   * Renders confirmation page.
   */
  public waitConfirmation(personCreated: PersonInfo): void {
    this.personId = '0'; // personCreated.personId;
    this.showConfirmation = true;
  }

}
