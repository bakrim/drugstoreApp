import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDossierPharmacie } from 'app/shared/model/dossier-pharmacie.model';
import { getEntities as getDossierPharmacies } from 'app/entities/dossier-pharmacie/dossier-pharmacie.reducer';
import { IDossierAutre } from 'app/shared/model/dossier-autre.model';
import { getEntities as getDossierAutres } from 'app/entities/dossier-autre/dossier-autre.reducer';
import { IEtapeValidation } from 'app/shared/model/etape-validation.model';
import { getEntity, updateEntity, createEntity, reset } from './etape-validation.reducer';

export const EtapeValidationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dossierPharmacies = useAppSelector(state => state.dossierPharmacie.entities);
  const dossierAutres = useAppSelector(state => state.dossierAutre.entities);
  const etapeValidationEntity = useAppSelector(state => state.etapeValidation.entity);
  const loading = useAppSelector(state => state.etapeValidation.loading);
  const updating = useAppSelector(state => state.etapeValidation.updating);
  const updateSuccess = useAppSelector(state => state.etapeValidation.updateSuccess);

  const handleClose = () => {
    navigate('/etape-validation');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDossierPharmacies({}));
    dispatch(getDossierAutres({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...etapeValidationEntity,
      ...values,
      dossierPharmacie: dossierPharmacies.find(it => it.id.toString() === values.dossierPharmacie.toString()),
      dossierAutre: dossierAutres.find(it => it.id.toString() === values.dossierAutre.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          date: displayDefaultDateTime(),
        }
      : {
          ...etapeValidationEntity,
          date: convertDateTimeFromServer(etapeValidationEntity.date),
          dossierPharmacie: etapeValidationEntity?.dossierPharmacie?.id,
          dossierAutre: etapeValidationEntity?.dossierAutre?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="drugStoreApp.etapeValidation.home.createOrEditLabel" data-cy="EtapeValidationCreateUpdateHeading">
            <Translate contentKey="drugStoreApp.etapeValidation.home.createOrEditLabel">Create or edit a EtapeValidation</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="etape-validation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('drugStoreApp.etapeValidation.libelle')}
                id="etape-validation-libelle"
                name="libelle"
                data-cy="libelle"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.etapeValidation.description')}
                id="etape-validation-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.etapeValidation.date')}
                id="etape-validation-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('drugStoreApp.etapeValidation.decision')}
                id="etape-validation-decision"
                name="decision"
                data-cy="decision"
                type="text"
              />
              <ValidatedField
                id="etape-validation-dossierPharmacie"
                name="dossierPharmacie"
                data-cy="dossierPharmacie"
                label={translate('drugStoreApp.etapeValidation.dossierPharmacie')}
                type="select"
              >
                <option value="" key="0" />
                {dossierPharmacies
                  ? dossierPharmacies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="etape-validation-dossierAutre"
                name="dossierAutre"
                data-cy="dossierAutre"
                label={translate('drugStoreApp.etapeValidation.dossierAutre')}
                type="select"
              >
                <option value="" key="0" />
                {dossierAutres
                  ? dossierAutres.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/etape-validation" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EtapeValidationUpdate;
