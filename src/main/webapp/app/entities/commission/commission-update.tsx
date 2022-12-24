import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRepresentant } from 'app/shared/model/representant.model';
import { getEntities as getRepresentants } from 'app/entities/representant/representant.reducer';
import { IDossierPharmacie } from 'app/shared/model/dossier-pharmacie.model';
import { getEntities as getDossierPharmacies } from 'app/entities/dossier-pharmacie/dossier-pharmacie.reducer';
import { ICommission } from 'app/shared/model/commission.model';
import { getEntity, updateEntity, createEntity, reset } from './commission.reducer';

export const CommissionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const representants = useAppSelector(state => state.representant.entities);
  const dossierPharmacies = useAppSelector(state => state.dossierPharmacie.entities);
  const commissionEntity = useAppSelector(state => state.commission.entity);
  const loading = useAppSelector(state => state.commission.loading);
  const updating = useAppSelector(state => state.commission.updating);
  const updateSuccess = useAppSelector(state => state.commission.updateSuccess);

  const handleClose = () => {
    navigate('/commission');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRepresentants({}));
    dispatch(getDossierPharmacies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...commissionEntity,
      ...values,
      representantLists: mapIdList(values.representantLists),
      dossierPharmacie: dossierPharmacies.find(it => it.id.toString() === values.dossierPharmacie.toString()),
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
          ...commissionEntity,
          date: convertDateTimeFromServer(commissionEntity.date),
          representantLists: commissionEntity?.representantLists?.map(e => e.id.toString()),
          dossierPharmacie: commissionEntity?.dossierPharmacie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="drugStoreApp.commission.home.createOrEditLabel" data-cy="CommissionCreateUpdateHeading">
            <Translate contentKey="drugStoreApp.commission.home.createOrEditLabel">Create or edit a Commission</Translate>
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
                  id="commission-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('drugStoreApp.commission.date')}
                id="commission-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('drugStoreApp.commission.decision')}
                id="commission-decision"
                name="decision"
                data-cy="decision"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.commission.motif')}
                id="commission-motif"
                name="motif"
                data-cy="motif"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.commission.representantList')}
                id="commission-representantList"
                data-cy="representantList"
                type="select"
                multiple
                name="representantLists"
              >
                <option value="" key="0" />
                {representants
                  ? representants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="commission-dossierPharmacie"
                name="dossierPharmacie"
                data-cy="dossierPharmacie"
                label={translate('drugStoreApp.commission.dossierPharmacie')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/commission" replace color="info">
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

export default CommissionUpdate;
